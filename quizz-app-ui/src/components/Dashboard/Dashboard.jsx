import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import apiFetchFunc from "../../apiFetchFunc";

export default function Dashboard() {
  const [surveys, setSurveys] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSurveys = async () => {
      try {
        setLoading(true);
        const response = await apiFetchFunc("/surveyController/getSurveyList");

        if (!response.ok) {
          throw new Error("Не удалось загрузить список тестов");
        }
        const data = await response.json();
        setSurveys(data.result);
      } catch (err) {
        if (err.name === "UnauthorizedError") {
          navigate("/login", { replace: true });
          return;
        }
        setError(err.message);
        setLoading(false);
      }
      setLoading(false);
    };

    fetchSurveys();
  }, []);

  if (loading) {
    return <div style={{ padding: "20px" }}>Загрузка опросов</div>;
  }

  if (error) {
    return (
      <div style={{ color: "red", padding: "20px" }}>
        Ошибка при загрузке тестов: {error}
      </div>
    );
  }

  return (
    <div style={{ padding: "20px" }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <h1>Доступные тесты</h1>
      </div>

      {surveys.length === 0 ? (
        <p>Пока нет доступных тестов</p>
      ) : (
        <div style={{ display: "grid", gap: "20px", marginTop: "20px" }}>
          {surveys.map((survey) => (
            <div
              key={survey.surveyId}
              style={{
                border: "1px solid #ccc",
                borderRadius: "8px",
                padding: "16px",
                backgroundColor: "#f9f9f9",
              }}
            >
              <h2 style={{ margin: "0 0 8px 0" }}>{survey.title}</h2>
              {survey.description && (
                <p style={{ margin: "0 0 12px 0" }}>{survey.description}</p>
              )}
              <button
                onClick={() => navigate(`/test/${survey.surveyId}`)}
                style={{ padding: "8px 16px", cursor: "pointer" }}
              >
                Подробнее
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
