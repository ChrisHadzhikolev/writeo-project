import http from "../http-common";
import authHeader from './authHeader';

class ArticleDataService {
    getAll() {
        return http.get("/article/all", {headers: authHeader()});
    }
    getAllAvailable() {
        return http.get("/article/available", {headers: authHeader()});
    }
    getAllByAuthor(data) {
        return http.post("/article/author", data, {headers: authHeader()});
    }

    get(id) {
        return http.get(`/article/${id}`, {headers: authHeader()});
    }
    getArticleTitle(id) {
        return http.get(`/article/title/${id}`, {headers: authHeader()});
    }

    create(data) {
        return http.post("/article/add", data, {headers: authHeader()});
    }

    update(id, data) {
        return http.put(`/article/update/${id}`, data, {headers: authHeader()});
    }

    articleSold(id){
        return http.put(`/article/sold/${id}`);
    }

    delete(id) {
        return http.delete(`/article/delete/${id}`, {headers: authHeader()});
    }

    deleteAll() {
        return http.delete(`/article/deleteAll`, {headers: authHeader()});
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new ArticleDataService();
