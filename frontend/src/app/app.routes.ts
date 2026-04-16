import { Routes } from '@angular/router';

import { LoginComponent } from './pages/auth/login/login';
import { RegisterComponent } from './pages/auth/register/register';

// import { StudentDashboardComponent } from './pages/dashboard/student-dashboard/student-dashboard.component';
// import { FacultyDashboardComponent } from './pages/dashboard/faculty-dashboard/faculty-dashboard.component';
// import { AdminDashboardComponent } from './pages/dashboard/admin-dashboard/admin-dashboard.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // AUTH
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // DASHBOARDS
//   { path: 'student-dashboard', component: StudentDashboardComponent },
//   { path: 'faculty-dashboard', component: FacultyDashboardComponent },
//   { path: 'admin-dashboard', component: AdminDashboardComponent },

  { path: '**', redirectTo: 'login' }
];