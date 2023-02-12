import React, { useEffect, useState } from "react";
import styles from "./App.module.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { LoginPage, StoryListPage, StoryDetailPage, PointPage } from "./pages";
function App() {
  // Should change to check the Token session
  const checkIfLogin = () => {
    // console.log(
    //   'localStorage.getItem("isLogin"):',
    //   localStorage.getItem("isLogin")
    // );
    // return localStorage.getItem("isLogin") === "True";
    return true;
  };

  return (
    <div className={styles.App}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route
            path="/story/list"
            element={checkIfLogin() ? <StoryListPage /> : <Navigate to="/" />}
          />
          <Route
            path="/story/detail/:storyID"
            element={checkIfLogin() ? <StoryDetailPage /> : <Navigate to="/" />}
          />
          <Route
            path="/story/result/:storyID"
            element={checkIfLogin() ? <PointPage /> : <Navigate to="/" />}
          />
          <Route path="*" element={<h1>404 not found</h1>} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
