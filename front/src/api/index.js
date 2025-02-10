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

// AGENCY
export const getCheapestFlat = async (id1, id2) => {
    return apiAgency.get(`/get-cheapest/${id1}/${id2}`)
        .then(response => response.data)
        .catch(error => {
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
        });
};

export const getOrderedByTimeToMetro = async (byTransport, desc) => {
    return apiAgency.get(`/get-ordered-by-time-to-metro/${byTransport}/${desc}`)
        .then(response => response.data)
        .catch(error => {
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
        });
};

// HOUSING
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
    useToast();
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

/**
 * Создать новую квартиру (POST)
 * @param {Object} flatData - Данные квартиры
 */
export const createFlat = async (flatData) => {
    const xmlData = convertToXML(flatData);
    try {
        await apiHousing.post(`/flats`, xmlData, {
            headers: { "Content-Type": "application/xml" },
        });
    } catch (error) {
        console.error("Ошибка при создании квартиры:", error);
        throw new Error("Не удалось добавить квартиру");
    }
};

/**
 * Обновить существующую квартиру (PUT)
 * @param {number} id - ID квартиры
 * @param {Object} flatData - Данные квартиры
 */
export const updateFlat = async (id, flatData) => {
    const xmlData = convertToXML(flatData);
    try {
        await apiHousing.put(`/flats/${id}`, xmlData, {
            headers: { "Content-Type": "application/xml" },
        });
    } catch (error) {
        console.error("Ошибка при обновлении квартиры:", error);
        throw new Error("Не удалось обновить квартиру");
    }
};

/**
 * Конвертирует объект в XML-формат
 * @param {Object} flatData - Объект квартиры
 * @returns {string} - XML-строка
 */
const convertToXML = (flatData) => {
    return `<?xml version="1.0" encoding="UTF-8"?>
<FlatCreateRequest>
  <name>${flatData.name}</name>
  <coordinates>
    <x>${flatData.coordinates.x}</x>
    <y>${flatData.coordinates.y}</y>
  </coordinates>
  <area>${flatData.area}</area>
  <numberOfRooms>${flatData.numberOfRooms}</numberOfRooms>
  <livingSpace>${flatData.livingSpace}</livingSpace>
  <new>${flatData.isNew}</new>
  <transport>${flatData.transport}</transport>
  <house>
    <name>${flatData.house.name}</name>
    <year>${flatData.house.year}</year>
    <numberOfFloors>${flatData.house.numberOfFloors}</numberOfFloors>
    <numberOfLifts>${flatData.house.numberOfLifts}</numberOfLifts>
  </house>
</FlatCreateRequest>`;
};

export { apiHousing, apiAgency };