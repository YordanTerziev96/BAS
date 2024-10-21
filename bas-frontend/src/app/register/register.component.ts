import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {AuthService} from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule], // Add ReactiveFormsModule to the imports
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: string;

  // Inject FormBuilder to help create the form group
  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      repeatedPassword: ['', Validators.required],
    }, {validators: this.passwordsMatchValidator}); // Custom validator for matching passwords
  }

  // Custom validator to check if passwords match
  passwordsMatchValidator(form: FormGroup): { [key: string]: boolean } | null {
    const password = form.get('password')?.value;
    const repeatedPassword = form.get('repeatedPassword')?.value;
    if (password && repeatedPassword && password !== repeatedPassword) {
      return {'passwordMismatch': true};
    }
    return null;
  }

  onSubmit(): void {
    // Reset error message on each submit attempt
    this.errorMessage = '';

    // Check if the form is valid
    if (this.registerForm.valid) {
      // Send the form data to the backend API
      this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          console.log('Registration successful:', response);
          // Redirect the user to the login page after successful registration
          this.router.navigate(['/login']);
        },
        error: (error) => {
          console.error('Registration failed:', error);
          // Handle the error (e.g., show a message to the user)
          this.errorMessage = error; // Set the error message to be displayed
        }
      });
    } else {
      console.log('Form is invalid');
      // Optionally, you can highlight the invalid fields or show a message to the user
      this.errorMessage = 'Please fill in all required fields correctly.';
    }
  }

  get formControls(): { [key: string]: AbstractControl } {
    return this.registerForm.controls;
  }
}
