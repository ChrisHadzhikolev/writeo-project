import http from "../http-common";

class SellDataService {
    getAll() {
        return http.get("/sell/all");
    }

    get(id) {
        return http.get(`/sell/${id}`);
    }

    create(data) {
        return http.post("/sell/add", data);
    }

    update(id, data) {
        return http.put(`/sell/update/${id}`, data);
    }

    delete(id) {
        return http.delete(`/sell/delete/${id}`);
    }

    deleteAll() {
        return http.delete(`/sell/deleteAll`);
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new SellDataService();
