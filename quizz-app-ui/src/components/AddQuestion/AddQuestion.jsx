import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import apiFetchFunc from '../../apiFetchFunc'

export default function AddQuestion() {
  const { surveyId } = useParams();
  const navigate = useNavigate();
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  function addQuestion() {
    const newQuestion = {
      questionText: "",
      orderIndex: questions.length,
      options: [],
    };
    setQuestions([...questions, newQuestion]);
  }

  function removeQuestion(index) {
    const updated = [...questions];
    updated.splice(index, 1);
    updated.forEach((q, idx) => (q.orderIndex = idx));
    setQuestions(updated);
  }

  function updateQuestionText(index, text) {
    const updated = [...questions];
    updated[index].questionText = text;
    setQuestions(updated);
  }

  function addOption(questionIndex) {
    const updated = [...questions];
    const newOption = {
      optionText: "",
      orderIndex: updated[questionIndex].options.length,
      isCorrect: false,
    };
    updated[questionIndex].options.push(newOption);
    setQuestions(updated);
  }

  function updateOptionText(qIndex, oIndex, text) {
    const updated = [...questions];
    updated[qIndex].options[oIndex].optionText = text;
    setQuestions(updated);
  }

  function updateOptionCorrect(qIndex, oIndex, isCorrect) {
    const updated = [...questions];
    updated[qIndex].options[oIndex].isCorrect = isCorrect;
    setQuestions(updated);
  }

  function removeOption(qIndex, oIndex) {
    const updated = [...questions];
    updated[qIndex].options.splice(oIndex, 1);
    updated[qIndex].options.forEach((opt, idx) => (opt.orderIndex = idx));
    setQuestions(updated);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setLoading(true);

    if (questions.length === 0) {
      setError("Добавьте хотя бы один вопрос");
      setLoading(false);
      return;
    }

    for (let i = 0; i < questions.length; i++) {
      if (!questions[i].questionText.trim()) {
        setError(`Вопрос ${i + 1} не может быть пустым`);
        setLoading(false);
        return;
      }
      if (questions[i].options.length === 0) {
        setError(`У вопроса ${i + 1} должен быть хотя бы один вариант ответа`);
        setLoading(false);
        return;
      }
      for (let j = 0; j < questions[i].options.length; j++) {
        if (!questions[i].options[j].optionText.trim()) {
          setError(`У вопроса ${i + 1} вараинт ${j + 1} не может быть пустым`);
          setLoading(false);
          return;
        }
      }
    }

    try {
      const payload = {
        surveyId: surveyId,
        questionsWithOptions: questions,
      };

      const response = await apiFetchFunc(
        "/surveyController/addQuestionsWithOptionsToSurvey",
        {
          method: "POST",
          body: JSON.stringify(payload),
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      if (!response.ok) {
        throw new Error("Ошибка сохранения вопросов");
      }

      navigate("/dashboard");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div style={{ padding: "20px", maxWidth: "800px", margin: "0 auto" }}>
      <h1>Добавление вопросов для теста</h1>
      {error && (
        <div style={{ color: "red", marginBottom: "16px" }}>{error}</div>
      )}
      <form onSubmit={handleSubmit}>
        {questions.map((question, qIdx) => (
          <div
            key={qIdx}
            style={{
              border: "1px solid #ccc",
              borderRadius: "8px",
              padding: "16px",
              marginBottom: "20px",
              backgroundColor: "#fafafa",
            }}
          >
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <h3>Вопрос для {qIdx + 1}</h3>
              <button
                type="button"
                onClick={() => removeQuestion(qIdx)}
                style={{ color: "red" }}
              >
                Удалить вопрос
              </button>
            </div>
            <div style={{ marginBottom: "12px" }}>
              <label>Текст вопроса</label>
              <input
                type="text"
                value={question.questionText}
                onChange={(event) =>
                  updateQuestionText(qIdx, event.target.value)
                }
                style={{ width: "100%", padding: "6px", marginTop: "4px" }}
              />
            </div>

            <div>
              <strong>Варианты ответов:</strong>
              {question.options.map((option, oIdx) => (
                <div
                  key={oIdx}
                  style={{ marginTop: "8px", paddingLeft: "20px" }}
                >
                  <div
                    style={{
                      display: "flex",
                      gap: "8px",
                      alignItems: "center",
                    }}
                  >
                    <input
                      type="text"
                      value={option.optionText}
                      onChange={(event) =>
                        updateOptionText(qIdx, oIdx, event.target.value)
                      }
                      placeholder={`Вариант ${oIdx + 1}`}
                      style={{ flex: 1, padding: "6px" }}
                    />
                    <label style={{display: 'flex', alignItems: 'center', gap: '4px'}}>
                        <input type='checkbox' checked={option.isCorrect} onChange={(event) => updateOptionCorrect(qIdx, oIdx, event.target.checked)}/>
                        Правильный
                    </label>
                    <button type="button" onClick={() => removeOption(qIdx, oIdx)}>
                        Удалить вариант ответа
                    </button>
                  </div>
                </div>
              ))}
              <button type="button" onClick={() => addOption(qIdx)} style={{marginTop: '8px', fontSize: '14px'}}>
                Добавить вараинт ответа
              </button>
            </div>
          </div>
        ))}
        <div style={{marginTop: '20px', display: 'flex', gap: '12px'}}>
            <button type="button" onClick={addQuestion}>
                Добавить вопрос
            </button>
            <button type="submit" disabled={loading} style={{padding: '10px 20px', backgroundColor: '#28a745', color: 'white', border: 'none', borderRadius: '4px', cursor: loading ? 'default': 'pointer',}}>
                {loading ? 'Сохранение...' : 'Завершить и сохранить тест'}
            </button>
            <button type="button" onClick={() => navigate('/dashboard')} style={{padding: '10px 20 px', backgroundColor: '#6c757d', color: 'white', border: 'none', borderRadius: '4px'}}>
                Отмена
            </button>
        </div>
      </form>
    </div>
  );
}
