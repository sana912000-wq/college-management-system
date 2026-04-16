import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  submitted = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      role: ['', Validators.required],
      email: [{ value: '', disabled: true }, [Validators.required, Validators.email]],
      password: [{ value: '', disabled: true }, Validators.required]
    });

    // Enable/Disable fields based on role
    this.loginForm.get('role')?.valueChanges.subscribe(role => {
      if (role) {
        this.loginForm.get('email')?.enable();
        this.loginForm.get('password')?.enable();
      } else {
        this.loginForm.get('email')?.disable();
        this.loginForm.get('password')?.disable();
      }
    });
  }

  get f() {
    return this.loginForm.controls;
  }

  onLogin() {
    console.log("Login clicked"); // DEBUG

    this.submitted = true;

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const role = this.loginForm.value.role;
    const { email, password } = this.loginForm.getRawValue();

    let url = '';

    switch (role) {
      case 'STUDENT':
        url = 'http://localhost:8080/api/auth/student/login';
        break;
      case 'FACULTY':
        url = 'http://localhost:8080/api/auth/faculty/login';
        break;
      case 'ADMIN':
        url = 'http://localhost:8080/api/auth/admin/login';
        break;
    }

    this.http.post<any>(url, { email, password }).subscribe({
      next: (res) => {
        console.log(res);

        localStorage.setItem('token', res.token);
        localStorage.setItem('role', role);

        this.successMessage = 'Login successful! ✅';

        // Navigation after 1.5 seconds
        setTimeout(() => {
          if (role === 'STUDENT') {
            this.router.navigate(['/student-dashboard']);
          } else if (role === 'FACULTY') {
            this.router.navigate(['/faculty-dashboard']);
          } else {
            this.router.navigate(['/admin-dashboard']);
          }
        }, 1500);
      },
      error: (err) => {
        console.log(err);
        this.errorMessage = err.error?.message || 'Login failed';
      }
    });
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}