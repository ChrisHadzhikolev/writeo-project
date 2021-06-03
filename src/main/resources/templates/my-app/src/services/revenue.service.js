import http from "../http-common";
import authHeader from "./authHeader";

class RevenueDataService {
    getAll() {
        return http.get("/revenue/all", {headers: authHeader()});
    }

    get(id) {
        return http.get(`/revenue/${id}`, {headers: authHeader()});
    }

    create(data) {
        return http.post("/revenue/add", data,{headers: authHeader()});
    }

    update(id, data) {
        return http.put(`/revenue/update/${id}`, data,{headers: authHeader()});
    }

    delete(id) {
        return http.delete(`/revenue/delete/${id}`, {headers: authHeader()});
    }

    deleteAll() {
        return http.delete(`/revenue/deleteAll`, {headers: authHeader()});
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new RevenueDataService();
