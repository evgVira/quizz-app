import "../Header/Header.css";
import ButtonPage from "../ButtonPage/ButtonPage";
import { useNavigate } from "react-router-dom";

export default function Header() {
  const navigate = useNavigate();

  function handleLogout(){
    localStorage.removeItem('userId')
    localStorage.removeItem('token')
    navigate('/login')
  }

  return (
    <header>
      <img
        src="/logo.jpeg"
        style={{ display: "flex", width: "60px", marginBottom: "10px" }}
        onClick={() => navigate("/dashboard")}
      ></img>
      <ButtonPage
        navigateValue={"/create-survey"}
        navigatePageName={"Создать новый тест"}
      />
      <button onClick={() => handleLogout()} style={{padding: '6px 12px', cursor: 'pointer'}}>
        Выйти
      </button>
      <h3>QUIZZ APPLICATION</h3>
    </header>
  );
}
