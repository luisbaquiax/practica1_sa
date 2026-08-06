import { Component, inject, signal } from "@angular/core";
import { RouterLink } from "@angular/router";
import { SessionAuthService } from "../../services/session-auth.service";

@Component({
  selector: "app-session-dashboard",
  imports: [RouterLink],
  template: `
    <div class="space-y-6">
      <header class="space-y-1">
        <div class="flex items-center gap-2">
          <span class="text-2xl"></span>
          <h1 class="text-2xl font-bold text-white">
            Session-Based Authentication
          </h1>
        </div>
        <p class="text-slate-400 text-sm">
          El servidor (Node/Express en el puerto 3001) guarda el estado de la
          sesión. La cookie
          <code class="text-slate-300">connect.sid</code> viaja automáticamente
          con <code class="text-slate-300">credentials: 'include'</code>.
        </p>
      </header>

      <!-- Estado actual -->
      <div
        class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2"
      >
        <h2 class="text-sm font-medium text-slate-300">Estado actual</h2>
        @if (loading()) {
        <p class="text-slate-500 text-sm">Verificando sesión…</p>
        } @else if (auth.isLoggedIn()) {
        <p class="text-emerald-400">
          ● Sesión activa como <strong>{{ auth.username() }}</strong>
        </p>
        <a
          routerLink="/session/protected"
          class="inline-block text-sm text-cyan-400 hover:underline"
          >Ir a página protegida →</a
        >
        } @else {
        <p class="text-slate-500">○ No hay sesión activa</p>
        <a
          routerLink="/session/login"
          class="inline-block text-sm text-cyan-400 hover:underline"
          >Iniciar sesión
        </a>
        }
      </div>

      <!-- Acciones -->
      <div
        class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-3"
      >
        <h3 class="font-medium text-white">Acciones</h3>
        <div class="flex flex-wrap gap-2">
          <a
            routerLink="/session/login"
            class="px-3 py-1.5 rounded-md bg-slate-800 text-sm hover:bg-slate-700 transition"
            >Login</a
          >
          <a
            routerLink="/session/public"
            class="px-3 py-1.5 rounded-md bg-slate-800 text-sm hover:bg-slate-700 transition"
            >Página pública</a
          >
          <a
            routerLink="/session/protected"
            class="px-3 py-1.5 rounded-md bg-slate-800 text-sm hover:bg-slate-700 transition"
            >Página protegida</a
          >
        </div>
      </div>

      @if (message()) {
      <div [class]="messageClass()" class="rounded-md p-3 text-sm">
        {{ message() }}
      </div>
      }
    </div>
  `,
})
export class SessionDashboardComponent {
  auth = inject(SessionAuthService);
  loading = signal(true);
  message = signal<string | null>(null);

  constructor() {
    this.auth.checkSession().then(() => this.loading.set(false));
  }

  messageClass(): string {
    const m = this.message();
    if (!m) return "";
    return m.includes("exit") || m.includes("correcta")
      ? "bg-emerald-500/10 text-emerald-400 border border-emerald-500/30"
      : "bg-rose-500/10 text-rose-400 border border-rose-500/30";
  }
}
