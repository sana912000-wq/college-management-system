import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private fb: FormBuilder, private authService: AuthService) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      role: ['', Validators.required],

      name: [{ value: '', disabled: true }, Validators.required],
      email: [{ value: '', disabled: true }, [Validators.required, Validators.email]],
      phone: [{ value: '', disabled: true }, [
        Validators.required,
        Validators.pattern('^[0-9]{10}$')
      ]],

      // 🔥 PASSWORD VALIDATION
      password: [{ value: '', disabled: true }, [
        Validators.required,
        Validators.minLength(6)
      ]],

      department: [{ value: '', disabled: true }],
      designation: [{ value: '', disabled: true }],
      enrollmentYear: [{ value: '', disabled: true }]
    });

    // ✅ Enable fields only after role selection
    this.registerForm.get('role')?.valueChanges.subscribe(role => {
      const controls = [
        'name', 'email', 'phone', 'password',
        'department', 'designation', 'enrollmentYear'
      ];

      if (role) {
        controls.forEach(c => this.registerForm.get(c)?.enable());
      } else {
        controls.forEach(c => this.registerForm.get(c)?.disable());
      }
    });
  }

  onRegister() {
    this.errorMessage = '';
    this.successMessage = '';

    if (this.registerForm.invalid) {
      this.errorMessage = 'Please fill all required fields correctly';
      return;
    }

    const role = this.registerForm.value.role;
    const formData = this.registerForm.getRawValue();

    this.authService.register(role, formData).subscribe({
      next: (res) => {
        this.successMessage = 'Registration successful ✅';
        this.registerForm.reset();
      },
      error: (err) => {
        console.log(err);
        this.errorMessage = err.error?.message || 'Registration failed ❌';
      }
    });
  }
}