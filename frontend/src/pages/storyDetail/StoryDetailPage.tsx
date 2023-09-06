import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import styles from "./StoryDetailPage.module.css";
import _axios from "../../axios";
import { useNavigate, useParams } from "react-router-dom";
import { useWebSocket } from "../../customHook/useWebSocket";

interface StoryType {
  id: string;
  name: string;
  point: string;
  status: string;
}

export const StoryDetailPage: React.FC = () => {
  const { t } = useTranslation();
  const { storyID } = useParams();
  const [storyDetailState, setStoryDetailState] = useState<StoryType | null>(
    null
  );
  const [msgState, setMsgState] = useState<string>("");
  const { stompClient } = useWebSocket();
  const selectOption = ["1", "2", "3", "5", "8", "13", "no_idea", "resign"];
  const navigate = useNavigate();

  // Get the story detail from backend
  const getStoryDetail = () => {
    _axios
      .get("/story/detail/", {
        params: {
          storyID,
        },
      })
      .then((response) => {
        const data = response.data;
        if (data.status.code === "SUCCESS") {
          setStoryDetailState(data.status.payload.story);
        } else {
          setMsgState("Error: Cannot Connect Server!");
        }
      })
      .catch((error) => {
        setMsgState(`Error: ${error}`);
      });
  };

  // Submit the Point Esatimate to backend
  const handlePointEstimate = () => {
    setMsgState("Voted!");
    const selectedPoint = document.getElementById("point") as HTMLSelectElement;
    stompClient.send(
      "/send/update-user-vote",
      {},
      JSON.stringify({
        email: localStorage.getItem("email"),
        point: selectedPoint.value,
        storyID,
      })
    );
  };

  useEffect(() => {
    getStoryDetail();
    // Get response after all the user voted
    stompClient?.subscribe(
      "/subscribe/update-user-vote",
      (frame) => {
        const data = JSON.parse(JSON.parse(frame.body).body);
        console.log("stompClient?.subscribe: ", data);
        if (data.status.payload.allUserVoted === true) {
          navigate(`/story/result/${storyID}`);
        }
      },
      (error) => {
        console.error("Error while subscribing:", error);
      }
    );
  }, [stompClient]);

  return (
    <div className={styles["story-box"]}>
      <h2>
        {t("story_detail_page.story")}:&nbsp;
        {storyDetailState && storyDetailState.name}
      </h2>

      <div className={styles["selector-section"]}>
        <div className={styles["selector-desc"]}>
          {t("story_detail_page.point_estimate")}:
        </div>
        <select name="point" id="point">
          {selectOption.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      </div>

      {msgState && (
        <div className={styles["msg"]}>
          <label>{msgState}</label>
        </div>
      )}

      <button onClick={() => handlePointEstimate()}>
        {t("story_detail_page.submit")}
      </button>
    </div>
  );
};
