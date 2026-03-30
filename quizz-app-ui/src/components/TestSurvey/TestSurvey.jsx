import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import apiFetchFunc from "../../apiFetchFunc";

export default function TestSurvey() {
  const { surveyId } = useParams();
  const navigate = useNavigate();

  const [surveyDetails, setSurveyDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [testStarted, setTestStarted] = useState(false);
  const [userResponseId, setUserResponseId] = useState(null);
  const [answers, setAnswers] = useState({});
  const [finishing, setFinishing] = useState(false);
  const [result, setResult] = useState(null);

  useEffect(() => {
    async function fetchSurveyDetails() {
      try {
        const response = await apiFetchFunc(
          `/surveyController/getSurveyDetails/${surveyId}`,
        );
        if (!response.ok) throw new Error("Не удалось загрузить тест");
        const data = await response.json();
        setSurveyDetails(data.result);
      } catch (e) {
        setError(e.message);
      } finally {
        setLoading(false);
      }
    }
    fetchSurveyDetails();
  }, [surveyId]);

  async function startTest() {
    try {
      const payload = {
        surveyId,
        userId: localStorage.getItem("userId"),
        startTryTime: new Date().toISOString(),
        finishTryTime: null,
      };
      const response = await apiFetchFunc(
        "/userResponseController/addUserResponse",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload),
        },
      );
      if (!response.ok) {
        let errorMessage = "Ошибка начала теста";
        try {
          const errorData = await response.json();
          errorMessage = errorData.errorMessage || errorMessage;
        } catch (e) {}
        throw new Error(errorMessage);
      }
      const data = await response.json();
      setUserResponseId(data.result.id);
      setTestStarted(true);
    } catch (e) {
      setError(e.message);
    }
  }

  async function removeTest() {
    try {
      const response = await apiFetchFunc(
        `/surveyRemoveController/removeSurvey/${surveyId}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({}),
        },
      );
      if (!response.ok) {
        throw new Error("Не удалось удалить тест");
      }
      navigate("/dashboard");
    } catch (e) {
      setError(e.message);
    }
  }

  async function sendAnswer(questionId, optionId) {
    if (!userResponseId) return;
    try {
      const payload = {
        userResponseId,
        optionId,
      };
      const response = await apiFetchFunc(
        "/userResponseController/addUserAnswer",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload),
        },
      );
      if (!response.ok) {
        let errorMessage = "Ошибка при сохранении ответа";
        try {
          const errorData = await response.json();
          errorMessage = errorData.errorMessage || errorMessage;
        } catch (e) {}
        throw new Error(errorMessage);
      }
    } catch (e) {
      setError(e.message);
    }
  }

  function handleOptionChange(questionId, optionId) {
    setAnswers((prev) => ({ ...prev, [questionId]: optionId }));
    sendAnswer(questionId, optionId);
  }

  async function finishTest() {
    if (!userResponseId) return;
    setFinishing(true);
    try {
      const response = await apiFetchFunc(
        `/userResponseController/finishTest/${userResponseId}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
        },
      );
      if (!response.ok) {
        let errorMessage = "Ошибка при завершении теста";
        try {
          const errorData = await response.json();
          errorMessage = errorData.errorMessage || errorMessage;
        } catch (e) {}
        throw new Error(errorMessage);
      }
      const contentType = response.headers.get("content-type");
      if (contentType && contentType.includes("application/json")) {
        const data = await response.json();
        setResult(data.result);
      } else {
        // если ответ пустой или не JSON, показываем заглушку
        setResult({ questionCount: 0, correctAnswerCount: 0 });
      }
    } catch (e) {
      setError(e.message);
    } finally {
      setFinishing(false);
    }
  }

  if (loading) return <div>Загрузка теста...</div>;
  if (error) return <div style={{ color: "red" }}>Ошибка: {error}</div>;
  if (!surveyDetails) return <div>Тест не найден</div>;

  const { survey, questions, options } = surveyDetails;

  if (!testStarted) {
    const isActive = survey.isActive;
    return (
      <div style={{ padding: "20px", maxWidth: "800px", margin: "0 auto" }}>
        <h1>{survey.title}</h1>
        <p>{survey.description}</p>
        <div style={{ margin: "16px 0", fontSize: "0.9em", color: "#000000" }}>
          <p>Создан: {new Date(survey.createDt).toLocaleString()}</p>
          <p>Обновлен: {new Date(survey.updateDt).toLocaleString()}</p>
          <p>Статус: {survey.isActive ? "Активен" : "Неактивен"}</p>
          <p>Автор: {survey.userLogin}</p>
        </div>
        {!isActive && (
          <p style={{ color: "red", marginTop: "8px" }}>
            Этот тест не доступен для прохождения
          </p>
        )}
        <button
          disabled={!isActive}
          onClick={startTest}
          style={{ padding: "10px 20px", cursor: "pointer" }}
        >
          Начать тест
        </button>
        <button
          onClick={removeTest}
          style={{ padding: "10px 20px", cursor: "pointer" }}
        >
          Удалить тест
        </button>
        <button
          onClick={() => navigate("/dashboard")}
          style={{ marginLeft: "10px", padding: "10px 20px" }}
        >
          Отмена
        </button>
      </div>
    );
  }

  if (result) {
    return (
      <div style={{ padding: "20px", maxWidth: "800px", margin: "0 auto" }}>
        <h1>Результат теста: {survey.title}</h1>
        <p>
          Правильных ответов: {result.correctAnswerCount} из{" "}
          {result.questionCount}
        </p>
        <button onClick={() => navigate("/dashboard")}>
          Вернуться к списку тестов
        </button>
      </div>
    );
  }

  return (
    <div style={{ padding: "20px", maxWidth: "800px", margin: "0 auto" }}>
      <h1>{survey.title}</h1>
      <p>{survey.description}</p>
      <div>
        {questions.map((question) => {
          const questionOptions = options.filter(
            (opt) => opt.questionId === question.questionId,
          );
          return (
            <div
              key={question.questionId}
              style={{
                marginBottom: "20px",
                border: "1px solid #ccc",
                padding: "16px",
                borderRadius: "8px",
              }}
            >
              <h3>{question.questionText}</h3>
              <div>
                {questionOptions.map((option) => (
                  <label
                    key={option.optionId}
                    style={{ display: "block", marginBottom: "8px" }}
                  >
                    <input
                      type="radio"
                      name={`question_${question.questionId}`}
                      value={option.optionId}
                      checked={answers[question.questionId] === option.optionId}
                      onChange={() =>
                        handleOptionChange(question.questionId, option.optionId)
                      }
                    />
                    {option.optionText}
                  </label>
                ))}
              </div>
            </div>
          );
        })}
      </div>
      <button
        onClick={finishTest}
        disabled={finishing}
        style={{
          padding: "10px 20px",
          backgroundColor: finishing ? "#ccc" : "#28a745",
          color: "white",
          border: "none",
          cursor: finishing ? "default" : "pointer",
        }}
      >
        {finishing ? "Завершение..." : "Завершить тест"}
      </button>
    </div>
  );
}
