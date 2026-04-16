import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  registerStudent(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/student/register`, data);
  }

  loginStudent(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/student/login`, data);
  }

  registerFaculty(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/faculty/register`, data);
  }

  loginFaculty(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/faculty/login`, data);
  }

  registerAdmin(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/admin/register`, data);
  }

  loginAdmin(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/admin/login`, data);
  }


  register(role: string, data: any): Observable<any> {
    switch (role) {
      case 'STUDENT':
        return this.registerStudent(data);
      case 'FACULTY':
        return this.registerFaculty(data);
      case 'ADMIN':
        return this.registerAdmin(data);
      default:
        throw new Error('Invalid role');
    }
  }

  login(role: string, data: any): Observable<any> {
    switch (role) {
      case 'STUDENT':
        return this.loginStudent(data);
      case 'FACULTY':
        return this.loginFaculty(data);
      case 'ADMIN':
        return this.loginAdmin(data);
      default:
        throw new Error('Invalid role');
    }
  }



  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/forgot-password`, { email });
  }

  resetPassword(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/reset-password`, data);
  }

  changePassword(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/change-password`, data);
  }

  

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.clear();
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}