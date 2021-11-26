import Axios from 'axios'
import interceptors from './interceptorsCustom'
import {getCustomApiBaseUrl} from "./utils";

const apiClient = Axios.create({
    baseURL: getCustomApiBaseUrl(),
    timeout: 20000,
    headers: {'Content-type': 'application/x-www-form-urlencoded'},
    withCredentials: true
});
interceptors(apiClient);

export default apiClient;

