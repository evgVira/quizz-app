const API_BASE_URL = "http://localhost:8081";

export default async function apiFetchFunc(endpoint, options = {}) {
  const token = localStorage.getItem("token");
  const headers = {
    "Content-Type": "application/json",
    ...options.headers,
  };
  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (response.status === 401) {
    // Очищаем хранилище и выбрасываем ошибку с типом 'UNAUTHORIZED'
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    const error = new Error("Unauthorized");
    error.name = "UnauthorizedError";
    throw error;
  }

  return response;
}
