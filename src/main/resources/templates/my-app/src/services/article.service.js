import http from "../http-common";
import authHeader from '../logic/authHeader';

class ArticleDataService {
    getAll() {
        return http.get("/article/all");
    }

    get(id) {
        return http.get(`/article/${id}`);
    }

    create(data) {
        return http.post("/article/add", {data,  headers: authHeader()});
    }

    update(id, data) {
        return http.put(`/article/update/${id}`, data);
    }

    delete(id) {
        return http.delete(`/article/delete/${id}`);
    }

    deleteAll() {
        return http.delete(`/article/deleteAll`);
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new ArticleDataService();
