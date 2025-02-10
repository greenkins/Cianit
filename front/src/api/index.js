import axios from "axios";
import { xml2js } from "xml-js"; // Установи библиотеку: npm install xml-js
import { useToast } from "vue-toastification";

const apiHousing = axios.create({
    baseURL: import.meta.env.VITE_HOUSING_API_URL,
    headers: { "Content-Type": "application/xml" },
});

const apiAgency = axios.create({
    baseURL: import.meta.env.VITE_AGENCY_API_URL,
});

const parseXmlResponse = (xml) => {
    const json = xml2js(xml, { compact: true, ignoreDeclaration: true });
    // Если структура другая, пробуем несколько вариантов
    const responseData = json.Response || json.response || json;
    if (!responseData) {
        console.error("Ошибка: некорректный формат XML", json);
        throw new Error("Некорректный XML-ответ от сервера");
    }
    return responseData;
};


export const getFlats = async (params) => {
    try {
        if (params.filter) {params.filter = decodeURIComponent(params.filter);}
        const response = await apiHousing.get("/flats", { params });
        return parseXmlResponse(response.data);
    } catch (error) {
        console.error("Ошибка запроса к API", error);
        throw error;
    }
};

export const getFlatById = async (id) => {
    const toast = useToast();
    try {
        const response = await apiHousing.get(`/flats/${id}`);
        return response.data;
    } catch (error) {
        const toast = useToast();
        // Парсим XML-ответ
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(error.response.data, 'text/xml');
        // Извлекаем сообщение об ошибке
        const errorCode = error.code;
        const errorMessage = xmlDoc.querySelector('error')?.textContent || 'Произошла ошибка';
        const details = xmlDoc.querySelector('details')?.textContent || '';
        // Показываем уведомление
        toast.error(`${errorCode.valueOf()} ${errorMessage}${details ? `: ${details}` : ''}`, {
            timeout: 3000,
        });
    }

};

export async function getTransportEnums() {
    const response = await apiHousing.get("/enums/transport")
    return response.data.values;
}

export { apiHousing, apiAgency };