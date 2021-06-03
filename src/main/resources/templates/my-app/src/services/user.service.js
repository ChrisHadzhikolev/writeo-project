import http from "../http-common";
import authHeader from "./authHeader";
import AuthService from "./authentication.service"

class UserDataService {
    getAll() {
        return http.get("/user/all", {headers: authHeader()});
    }
    getAllAuthors() {
        return http.get("/user/authors", {headers: authHeader()});
    }
    getAllAdmins() {
        return http.get("/user/admins", {headers: authHeader()});
    }

    get(id) {
        if (AuthService.getCurrentUser().id === id){
            return http.get(`/user/${id}`, {headers: authHeader()});
        }else{
            return null
        }
    }
    getShopAuthor(id) {
            return http.get(`/user/buyer/${id}`);
    }

    create(data) {
        return http.post("/user/add", data,{headers: authHeader()});
    }

    update(id, data) {
        if (AuthService.getCurrentUser().id === id){
            return http.put(`/user/update/${id}`, data, {headers: authHeader()});
        }else{
            return null
        }
    }

    delete(id) {
        if (AuthService.getCurrentUser().id === id){
            return http.delete(`/user/delete/${id}`, {headers: authHeader()});
        }else{
            return null
        }
    }

    deleteAll() {
        return http.delete(`/user/deleteAll`, {headers: authHeader()});
    }

    // findByTitle(title) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new UserDataService();
