import { useEffect, useState } from "react";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

export const useWebSocket = () => {
  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  // const SOCKET_URL = "ws://localhost:3001/websocket";
  const SOCKET_URL = "http://localhost:3001/websocket";
  const headers = {};

  useEffect(() => {
    const socket = new SockJS(SOCKET_URL);
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      setStompClient(stompClient);
    }, (error) => {
      console.error("Error while subscribing:", error);
    });

    return () => {
      if (stompClient.connected) {
        stompClient.disconnect();
      }
    };
  }, []);

  return { stompClient };
};
