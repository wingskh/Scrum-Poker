import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { useWebSocket } from "../../customHook/useWebSocket";
import styles from "./StoryListPage.module.css";
import _axios from "../../axios";
import { StoryType } from "../interface/story";

// Page to display the list of Stories
export const StoryListPage: React.FC = () => {
  const { t } = useTranslation();
  const [storyState, setStoryState] = useState([]);
  const [errorMsgState, setErrorMsgState] = useState("");
  const { stompClient } = useWebSocket();
  const navigate = useNavigate();

  // Get the list of story from backend
  const loadStoryList = () => {
    _axios
      .get("/story/list")
      .then((response) => {
        const data = response.data;
        if (data.status.code === "SUCCESS") {
          setStoryState(data.status.payload.stories);
        } else {
          setErrorMsgState("Error: Cannot Connect Server!");
        }
      })
      .catch((error) => {
        setErrorMsgState(`Error: ${error}`);
      });
  };

  // Function to update the story status Action
  const sendSetActiveRequest = async (selectedID) => {
    const response = await _axios.post("/story/set-active", {
      email: localStorage.getItem("email"),
      selectedID,
    });
    return response.data;
  };

  // Function to handle the status udpate
  const handleSelectTopic = async (selectedID, status) => {
    setErrorMsgState("Loading...");
    if (status === "Completed") {
      setErrorMsgState("Error: Cannot Select a Completed Story");
    } else {
      const data = await sendSetActiveRequest(selectedID);
      console.log(data)
      if (data.status.code === "SUCCESS") {
        loadStoryList();
      } else {
        setErrorMsgState(data.status.message);
      }
    }
  };

  useEffect(() => {
    loadStoryList();
  }, []);

  useEffect(() => {
    // Get response after a story is set to active
    stompClient?.subscribe(
      "/subscribe/find-active-story",
      (frame) => {
        const data = JSON.parse(JSON.parse(frame.body).body);
        if (data.status.code === "SUCCESS") {
          navigate(`/story/detail/${data.status.payload.id}`);
        }
      },
      (error) => {
        console.error("Error while subscribing:", error);
      }
    );

    stompClient?.send("/send/find-active-story", {});
  }, [stompClient, storyState]);

  return (
    <center className={styles["stories-box"]}>
      <h2>{t("story_list_page.story_list")}</h2>
      {storyState.length ? (
        <ol>
          {storyState.map((story: StoryType) => (
            <div
              key={story["id"]}
              onClick={() => handleSelectTopic(story["id"], story["status"])}
              className={styles["story-item"]}
            >
              <li>{story["name"]}</li>
              <div
                className={`${styles["status-label"]} ${
                  styles[`status-${story["status"].toLowerCase()}`]
                }`}
              >
                {story["status"]}
              </div>
            </div>
          ))}
        </ol>
      ) : (
        "Loading..."
      )}
      {errorMsgState && (
        <div className={styles["error-msg"]}>
          <label>{errorMsgState}</label>
        </div>
      )}
    </center>
  );
};
