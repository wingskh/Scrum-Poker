// import _axios from "axios"
import axios from "axios";

let baseURL = null;

let axiosConfig = {
  baseURL: baseURL || "http://localhost:3001",
  headers: {
    "Content-Type": "application/x-www-form-urlencoded",
    "Access-Control-Allow-Headers": "Content-Type",
  },
  timeout: 10 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

// const axios = (baseURL) => {
//     const instance = _axios.create({
//             baseURL: baseURL || 'http://localhost:3000', //JSON-Server端口位置
//             timeout: 1000,
//         });
//      return instance;
// }

const _axios = axios.create(axiosConfig);
export default _axios;

// export {axios};
// export default axios(null);
