import http from "../http-common";

class UserDataService {
    getAll() {
        return http.get("/user/all");
    }

    get(id) {
        return http.get(`/user/${id}`);
    }

    create(data) {
        return http.post("/user/add", data);
    }

    update(id, data) {
        return http.put(`/user/update/${id}`, data);
    }

    delete(id) {
        return http.delete(`/user/delete/${id}`);
    }

    deleteAll() {
        return http.delete(`/user/deleteAll`);
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new UserDataService();
