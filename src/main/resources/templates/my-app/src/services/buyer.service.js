import http from "../http-common";
import authHeader from './authHeader';


class BuyerDataService {
    getAll() {
        return http.get("/buyer/all", {headers: authHeader()});
    }

    get(id) {
        return http.get(`/buyer/${id}`, {headers: authHeader()});
    }

    create(data) {
        return http.post("/buyer/add", data,{headers: authHeader()});
    }

    update(id, data) {
        return http.put(`/buyer/update/${id}`, data, {headers: authHeader()});
    }

    delete(id) {
        return http.delete(`/buyer/delete/${id}`,  {headers: authHeader()});
    }

    deleteAll() {
        return http.delete(`/buyer/deleteAll`, {headers: authHeader()});
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new BuyerDataService();
