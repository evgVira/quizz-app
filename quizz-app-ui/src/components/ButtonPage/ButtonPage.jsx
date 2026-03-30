import { useNavigate } from "react-router-dom";
import "./ButtonPage.css";

export default function ButtonPage({ navigateValue, navigatePageName }) {
  const navigate = useNavigate();
  return (
    <button type="button" onClick={() => navigate(navigateValue)}>
      {navigatePageName}
    </button>
  );
}
