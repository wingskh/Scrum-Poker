import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import styles from "./PointPage.module.css";
import _axios from "../../axios";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { PointType } from "../interface/point";


// Page to display point estimate page
export const PointPage: React.FC = () => {
  const { t } = useTranslation();
  const { storyID } = useParams();
  const [errorMsgState, setErrorMsgState] = useState<string>("");
  const selectOption = ["1", "2", "3", "5", "8", "13", "no_idea", "resign"];
  const navigate = useNavigate();
  const [pointResultState, setPointResultState] = useState<PointType | null>(
    null
  );

  const getPointResult = () => {
    _axios
      .get("/story/result/", {
        params: {
          storyID,
        },
      })
      .then((response) => {
        const data = response.data;
        console.log(data);
        if (data.status.code === "SUCCESS") {
          setPointResultState(data.status.payload);
        } else {
          setErrorMsgState("Error: Cannot Connect Server!");
        }
      })
      .catch((error) => {
        setErrorMsgState(`Error: ${error}`);
      });
  };

  const handleReturnStoryList = () => {
    navigate("/story/list");
  }

  useEffect(() => {
    getPointResult();
  }, []);

  return (
    <div className={styles["story-box"]}>
      <h2>
        {t("point_result_page.point_result")}:&nbsp;
        {/* {storyDetailState && storyDetailState.name} */}
      </h2>
      
      <table className={styles['point-table']}>
        <thead>
          <tr>
            <th>Email</th>
            <th>Point Estimate</th>
          </tr>
        </thead>
        <tbody>
          {
            pointResultState &&
            Object.entries(pointResultState).map(([key, value]) => (
              <tr key={key}>
                <td>{key}</td>
                <td>{value}</td>
              </tr>
            ))
          }
        </tbody>
      </table>

      {errorMsgState && (
        <div className={styles["err-msg"]}>
          <label>{errorMsgState}</label>
        </div>
      )}
      <button className={styles["return-btn"]} onClick={() => handleReturnStoryList()}>
        {t("point_result_page.return_btn")}
      </button>
    </div>
  );
};
