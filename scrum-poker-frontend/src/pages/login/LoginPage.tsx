import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import styles from "./LoginPage.module.css";
import { useNavigate } from "react-router-dom";
import _axios from "../../axios";

// Login page for the website
export const LoginPage: React.FC = () => {
  const { t } = useTranslation();
  const [userState, setUserState] = useState({
    password: "",
    email: "",
  });
  const [errorMsgState, setErrorMsgState] = useState("");
  const navigate = useNavigate();
  const inputFields = document.getElementsByClassName("inputField");

  // Handle the login function
  const submitHandler = async (e) => {
    e.preventDefault();
    await _axios
      .post("/user/login", {
        email: userState.email,
        password: userState.password,
      })
      .then((response) => {
        const data = response.data;
        if (data.status.code === "SUCCESS") {
          localStorage.setItem("isLogin", "True");
          localStorage.setItem("username", data.status.payload.username);
          localStorage.setItem("email", data.status.payload.email);
          navigate("/story/list");
        } else {
          setErrorMsgState("Error: Invalid Username/Password");
          for (let i = 0; i < inputFields.length; i++) {
            inputFields[i].classList.add(styles["error-input"]);
          }
          inputFields[0].classList.add(styles["error-input"]);
        }
      })
      .catch((error) => {
        console.log(`error: ${error}`);
        setErrorMsgState("Error: Cannot Connect Server!");
      });
  };

  // Clear the token info in localstorage
  const clearAllCache = () => {
    console.log("Clear All Cache");
    localStorage.clear();
  };

  useEffect(() => {
    clearAllCache();
    inputFields[0].addEventListener("animationend", (e) => {
      for (let i = 0; i < inputFields.length; i++) {
        inputFields[i].className = "inputField";
      }
    });

    return () => {
      const inputFields = document.getElementsByClassName("inputField");
      inputFields.length &&
        inputFields[0].removeEventListener("animationend", () => {});
    };
  }, []);

  return (
    <div className={styles["login-box"]}>
      <h2>{t("login_page.login")}</h2>
      <form onSubmit={submitHandler}>
        <div className={styles["user-box"]}>
          <input
            id="inputField"
            className="inputField"
            type="text"
            name=""
            required={true}
            autoComplete="new-password"
            onChange={(event) =>
              setUserState({ ...userState, email: event.target.value })
            }
          />
          <label>{t("login_page.email")}:</label>
        </div>
        <div className={styles["user-box"]}>
          <input
            className="inputField"
            type="password"
            name=""
            required={true}
            autoComplete="new-password"
            onChange={(event) =>
              setUserState({ ...userState, password: event.target.value })
            }
          />
          <label>{t("login_page.password")}:</label>
        </div>
        {errorMsgState && (
          <div className={styles["error-msg"]}>
            <label>{errorMsgState}</label>
          </div>
        )}
        <center>
          {/* <a href="#">{t("login_page.submit")}</a> */}
          <button type="submit">{t("login_page.submit")}</button>
        </center>
      </form>
    </div>
  );
};
