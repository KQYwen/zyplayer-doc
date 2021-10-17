import Axios from 'axios'
import interceptors from './interceptors'
import {getZyplayerApiBaseUrl} from "./utils";

const apiClient = Axios.create({
    baseURL: getZyplayerApiBaseUrl(),
    timeout: 20000,
    headers: {'Content-type': 'application/x-www-form-urlencoded'},
    withCredentials: true
});
interceptors(apiClient);

export default apiClient;

