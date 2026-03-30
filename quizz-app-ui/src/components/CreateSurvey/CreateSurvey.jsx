import { useState } from "react";
import { useNavigate } from "react-router-dom";
import apiFetchFunc from '../../apiFetchFunc'

export default function CreateSurvey() {
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [isActive, setIsActive] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    const createdById = localStorage.getItem('userId');

    try {
      const response = await apiFetchFunc(
        "/surveyController/createSurvey",
        {
          method: "POST",
          body: JSON.stringify({
            title,
            description,
            isActive,
            createdById,
          }),
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      if (!response.ok) {
        throw new Error("Ошибка создания теста");
      }

      const data = await response.json();
      const surveyId = data.result.surveyId;

      navigate(`/add-question/${surveyId}`);
    } catch (err) {
      setError(err.messasge);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "600px", margin: "0 auto" }}>
      <h1>Создать новый тест</h1>
      {error && (
        <div style={{ color: "red", marginBottom: "16px" }}>{error}</div>
      )}

      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "16px" }}>
          <label style={{ display: "block", marginBottom: "8px" }}>
            Название теста:
          </label>
          <input
            type="text"
            value={title}
            onChange={(event) => setTitle(event.target.value)}
            required
            style={{ width: "100%", padding: "8px", fontSize: "16px" }}
          />
        </div>

        <div style={{ marginBottom: "16px" }}>
          <label style={{ display: "block", marginBottom: "8px" }}>
            Описание:
          </label>
          <textarea
            type="text"
            value={description}
            onChange={(event) => setDescription(event.target.value)}
            rows="4"
            style={{ width: "100%", padding: "8px", fontSize: "16px" }}
          />
        </div>

        <div style={{ marginBottom: "16px" }}>
          <label style={{ display: "flex", alignItems: "center", gap: "8px" }}>
            <input
              type="checkbox"
              checked={isActive}
              onChange={(event) => setIsActive(event.target.checked)}
            />
            Активный (будет виден пользователям)
          </label>
        </div>

        <button
          type="submit"
          disabled={loading}
          style={{
            padding: "10px 20px",
            fontSize: "16px",
            cursor: "pointer",
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            borderRadius: "4px",
          }}
        >
          {loading ? "Создание..." : "Далее -> добавить вопросы"}
        </button>
        <button
          type="button"
          onClick={() => navigate("/dashboard")}
          style={{
            marginLeft: "10px",
            padding: "10px 20px",
            fontSize: "16px",
            cursor: "pointer",
            backgroundColor: "#6c757d",
            color: "white",
            border: "none",
            borderRadius: "4px",
          }}
        >
          Отмена
        </button>
      </form>
    </div>
  );
}
