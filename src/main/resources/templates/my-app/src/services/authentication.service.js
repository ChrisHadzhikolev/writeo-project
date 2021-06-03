import http from "../http-common";

class AuthenticationDataService {
    login(username, password){
        return http.post("/auth/signin", {username, password})
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                    //alert(JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));;
    }

    register(username, email, password) {
        console.log(username + email + password)
        var role = ["author"];
        return http.post( "/auth/signup", {
            username,
            email,
            password,
            role
        });
    }
}
export default new AuthenticationDataService();
