import axios from "axios";
import { xml2js } from "xml-js"; // Установи библиотеку: npm install xml-js

const apiHousing = axios.create({
    baseURL: import.meta.env.VITE_HOUSING_API_URL,
});

const apiAgency = axios.create({
    baseURL: import.meta.env.VITE_AGENCY_API_URL,
});

export { apiHousing, apiAgency };