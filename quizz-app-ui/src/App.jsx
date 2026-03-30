import Dashboard from "./components/Dashboard/Dashboard";
import Header from "./components/Header/Header";
import CreateSurvey from "./components/CreateSurvey/CreateSurvey";
import AddQuestion from "./components/AddQuestion/AddQuestion";
import TestSurvey from "./components/TestSurvey/TestSurvey";
import LoginPage from "./components/Login/Login";
import PrivateRoute from "./components/PrivateRoute/PrivateRoute";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

function App() {
  return (
    <>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/login" element={<LoginPage/>}/>
          <Route
            path="/dashboard"
            element={
              <PrivateRoute>
                <Dashboard />
              </PrivateRoute>
            }
          />
          <Route
            path="/create-survey"
            element={
              <PrivateRoute>
                <CreateSurvey />
              </PrivateRoute>
            }
          />
          <Route
            path="/add-question/:surveyId"
            element={
              <PrivateRoute>
                <AddQuestion />
              </PrivateRoute>
            }
          />
          <Route
            path="/test/:surveyId"
            element={
              <PrivateRoute>
                <TestSurvey />
              </PrivateRoute>
            }
          />
          <Route path="/" element={<Navigate to="/dashboard" />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
