import { Component, inject, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { SessionAuthService } from "../../services/session-auth.service";

@Component({
  selector: "app-session-login",
  imports: [FormsModule, RouterLink],
  template: `
    <div class="max-w-md mx-auto space-y-6">
      <header class="space-y-1">
        <div class="flex items-center gap-2">
          <span class="text-2xl"></span>
          <h1 class="text-2xl font-bold text-white">Login (Session-Based)</h1>
        </div>
        <p class="text-slate-400 text-sm">
          El servidor crea una sesión y setea la cookie
          <code class="text-slate-300">connect.sid</code> (httpOnly). No
          guardamos nada en el cliente.
        </p>
      </header>

      @if (auth.isLoggedIn()) {
      <div
        class="rounded-lg border border-emerald-500/30 bg-emerald-500/10 p-4 space-y-2"
      >
        <p class="text-emerald-400">
          Ya tienes una sesión activa como <strong>{{ auth.username() }}</strong
          >.
        </p>
        <a
          routerLink="/session/protected"
          class="inline-block text-sm text-cyan-400 hover:underline"
          >Ir a página protegida →</a
        >
      </div>
      } @else {
      <form
        (ngSubmit)="login()"
        class="space-y-4 rounded-lg border border-slate-800 bg-slate-900 p-6"
      >
        <div class="space-y-1.5">
          <label class="text-sm text-slate-300">Usuario</label>
          <input
            type="text"
            [(ngModel)]="username"
            name="username"
            autocomplete="username"
            class="w-full rounded-md bg-slate-800 border border-slate-700 px-3 py-2 text-sm text-white focus:border-cyan-500 focus:outline-none transition"
          />
        </div>
        <div class="space-y-1.5">
          <label class="text-sm text-slate-300">Contraseña</label>
          <input
            type="password"
            [(ngModel)]="password"
            name="password"
            autocomplete="current-password"
            class="w-full rounded-md bg-slate-800 border border-slate-700 px-3 py-2 text-sm text-white focus:border-cyan-500 focus:outline-none transition"
          />
        </div>

        @if (error()) {
        <p class="text-sm text-rose-400">{{ error() }}</p>
        }

        <button
          type="submit"
          [disabled]="loading()"
          class="w-full rounded-md bg-cyan-500 text-slate-950 font-medium py-2 text-sm hover:bg-cyan-400 transition disabled:opacity-50"
        >
          {{ loading() ? "Iniciando…" : "Iniciar sesión" }}
        </button>
      </form>
      }

      <a
        routerLink="/session"
        class="block text-center text-sm text-slate-500 hover:text-slate-300"
      >
        Volver al dashboard</a
      >
    </div>
  `,
})
export class SessionLoginComponent {
  auth = inject(SessionAuthService);
  private router = inject(Router);

  username = "";
  password = "";
  error = signal<string | null>(null);
  loading = signal(false);

  async login(): Promise<void> {
    this.error.set(null);
    this.loading.set(true);
    const result = await this.auth.login(this.username, this.password);
    this.loading.set(false);
    if (result.success) {
      this.router.navigate(["/session/protected"]);
    } else {
      this.error.set(result.message);
    }
  }
}
