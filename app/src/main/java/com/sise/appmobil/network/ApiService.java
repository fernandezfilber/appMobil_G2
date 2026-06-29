package com.sise.appmobil.network;

import com.sise.appmobil.models.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    
    // Obtener información del usuario autenticado
    @GET("api/auth/me")
    Call<BaseResponse<User>> getMe();

    // Puedes ir agregando los 50+ endpoints aquí progresivamente:
    // @GET("api/cursos")
    // Call<BaseResponse<List<Curso>>> getCursos();
}
