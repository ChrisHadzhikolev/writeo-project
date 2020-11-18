import http from "../http-common";
import authHeader from '../logic/authHeader';


class BuyerDataService {
    getAll() {
        return http.get("/buyer/all", {headers: authHeader()});
    }

    get(id) {
        return http.get(`/buyer/${id}`);
    }

    create(data) {
        return http.post("/buyer/add", data);
    }

    update(id, data) {
        return http.put(`/buyer/update/${id}`, data);
    }

    delete(id) {
        return http.delete(`/buyer/delete/${id}`);
    }

    deleteAll() {
        return http.delete(`/buyer/deleteAll`);
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new BuyerDataService();
