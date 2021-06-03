import http from "../http-common";
import authHeader from "./authHeader";

class SellDataService {
    getAll() {
        return http.get("/sell/all", {headers: authHeader()});
    }

    get(id) {
        return http.get(`/sell/${id}`, {headers: authHeader()});
    }

    create(data) {
        return http.post("/sell/add", data, {headers: authHeader()});
    }

    update(id, data) {
        return http.put(`/sell/update/${id}`, data,{headers: authHeader()});
    }

    delete(id) {
        return http.delete(`/sell/delete/${id}`, {headers: authHeader()});
    }

    deleteAll() {
        return http.delete(`/sell/deleteAll`, {headers: authHeader()});
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new SellDataService();
