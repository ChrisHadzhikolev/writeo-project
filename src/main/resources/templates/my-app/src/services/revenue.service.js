import http from "../http-common";

class RevenueDataService {
    getAll() {
        return http.get("/revenue/all");
    }

    get(id) {
        return http.get(`/revenue/${id}`);
    }

    create(data) {
        return http.post("/revenue/add", data);
    }

    update(id, data) {
        return http.put(`/revenue/update/${id}`, data);
    }

    delete(id) {
        return http.delete(`/revenue/delete/${id}`);
    }

    deleteAll() {
        return http.delete(`/revenue/deleteAll`);
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new RevenueDataService();
