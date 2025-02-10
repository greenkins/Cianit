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

/**
 * Удалить квартиру по ID
 * @param {number} id - ID квартиры
 */
export const deleteFlat = async (id) => {
    try {
        await apiHousing.delete(`/flats/${id}`);
    } catch (error) {
        console.error("Ошибка при удалении квартиры:", error);
        throw new Error("Не удалось удалить квартиру");
    }
};

/**
 * Удалить все квартиры в доме
 * @param {string} houseName - Название дома
 */
export const deleteFlatsByHouse = async (houseName) => {
    try {
        await apiHousing.delete(`/flats/house/${encodeURIComponent(houseName)}`);
    } catch (error) {
        console.error("Ошибка при удалении квартир в доме:", error);
        throw new Error("Не удалось удалить квартиры в доме");
    }
};

/**
 * Удалить все квартиры с указанным значением new
 * @param {boolean} isNew - Статус новостройки (true/false)
 */
export const deleteFlatsByNewStatus = async (isNew) => {
    try {
        await apiHousing.delete(`/flats/any-with-new/${isNew}`);
    } catch (error) {
        console.error("Ошибка при удалении квартир по статусу new:", error);
        throw new Error("Не удалось удалить квартиры с таким статусом new");
    }
};

export { apiHousing, apiAgency };