import { type } from "@testing-library/user-event/dist/type";
import React from "react";
import { useParams } from "react-router-dom";
import styles from "./Detail.module.css";

// type str = "str";
// interface str2 = "str";

export const DetailPage = () => {
  // var params = useParams<"touristRouteId">();
  // var params = useParams<MatchParams>();
  var params = useParams();
  return (
    <h1>
      旅游路线详情页, 路线id: {params.touristRouteId} {params.other}
    </h1>
  );
};
