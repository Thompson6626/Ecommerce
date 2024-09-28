import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private BASE_AUTH_URL = 'http://localhost:8088/api/v1/auth';
  // The register endpoint should send the most important stuff
  private LOGIN_URL = this.BASE_AUTH_URL + '/register'; // First time
  // The authentication will just be the email and password
  private REFRESH_URL = this.BASE_AUTH_URL + '/authenticate'; // To get the jwt token
  private tokenKey = 'authToken';


  constructor() { 

  }
}
