import React from 'react';
import { BrowserRouter as Router, Routes, Route, } from "react-router-dom";
import './App.css';
import SignUpView from "./views/SignupView";
import LoginView from "./views/LoginView";
import InstructorView from "./views/InstructorView";
import StudentView from "./views/StudentView";
import NotFoundView from "./views/NotFoundView";
import { BookingSystemContextProvider } from "./context/BookingSystemContext";
import { createTheme } from "@mui/material";
import { ThemeProvider } from "@mui/system";


function App(): React.ReactElement {
  const theme = createTheme();
  return (
    <BookingSystemContextProvider>
      <ThemeProvider theme={theme}>
        <Router>
        <Routes>
            <Route path="/student/:username" element={<StudentView/>}/>
            <Route path='/instructor/:username' element={<InstructorView/>}/>
            <Route path='/login' element={<LoginView/>}/>
            <Route path='/signup' element={<SignUpView/>}/>
            <Route path="/" element={<NotFoundView/>}/>
          </Routes>
        </Router>
      </ThemeProvider>
    </BookingSystemContextProvider>
  );
}

export default App;
