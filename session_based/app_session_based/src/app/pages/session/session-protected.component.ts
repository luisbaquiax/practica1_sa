import { Component, inject, signal, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterLink } from "@angular/router";
import { SessionAuthService } from "../../services/session-auth.service";

@Component({
  selector: "app-session-protected",
  imports: [FormsModule, RouterLink],
  template: `
    <div class="max-w-2xl mx-auto space-y-6">
      <header class="space-y-1">
        <h1 class="text-2xl font-bold text-white">Página Protegida</h1>
        <p class="text-slate-400 text-sm">
          GET /api/auth/protected — requiere cookie de sesión activa.
        </p>
      </header>

      <div
        class="rounded-lg border border-emerald-500/30 bg-emerald-500/10 p-4"
      >
        <p class="text-emerald-400">{{ serverMessage() }}</p>
      </div>

      <!-- Cambio de contraseña -->
      <div
        class="rounded-lg border border-slate-800 bg-slate-900 p-6 space-y-4"
      >
        <h2 class="font-medium text-white">Cambiar contraseña</h2>
        <p class="text-xs text-slate-500">
          POST /api/auth/change-password El servidor revoca las demás sesiones
          de tu usuario.
        </p>
        <div class="space-y-3">
          <div class="space-y-1.5">
            <label class="text-sm text-slate-300">Contraseña actual</label>
            <input
              type="password"
              [(ngModel)]="currentPwd"
              name="current"
              class="w-full rounded-md bg-slate-800 border border-slate-700 px-3 py-2 text-sm text-white focus:border-cyan-500 focus:outline-none"
            />
          </div>
          <div class="space-y-1.5">
            <label class="text-sm text-slate-300">Nueva contraseña</label>
            <input
              type="password"
              [(ngModel)]="newPwd"
              name="new"
              class="w-full rounded-md bg-slate-800 border border-slate-700 px-3 py-2 text-sm text-white focus:border-cyan-500 focus:outline-none"
            />
          </div>
          @if (msg()) {
          <p [class]="msgClass()" class="text-sm">{{ msg() }}</p>
          }
          <button
            (click)="changePassword()"
            [disabled]="loading()"
            class="rounded-md bg-cyan-500 text-slate-950 font-medium px-4 py-2 text-sm hover:bg-cyan-400 transition disabled:opacity-50"
          >
            {{ loading() ? "Actualizando…" : "Actualizar contraseña" }}
          </button>
        </div>
      </div>

      <!-- Logout -->
      <div class="flex gap-3">
        <button
          (click)="logout()"
          [disabled]="loggingOut()"
          class="rounded-md border border-rose-500/30 text-rose-400 px-4 py-2 text-sm hover:bg-rose-500/10 transition disabled:opacity-50"
        >
          {{ loggingOut() ? "Cerrando…" : "Cerrar sesión" }}
        </button>
        <a
          routerLink="/session"
          class="inline-block text-sm text-slate-500 hover:text-slate-300 self-center"
        >
          Dashboard</a
        >
      </div>
    </div>
  `,
})
export class SessionProtectedComponent implements OnInit {
  auth = inject(SessionAuthService);
  currentPwd = "";
  newPwd = "";
  msg = signal<string | null>(null);
  loading = signal(false);
  loggingOut = signal(false);
  serverMessage = signal("Cargando…");

  async ngOnInit(): Promise<void> {
    this.serverMessage.set(await this.auth.getProtectedMessage());
  }

  msgClass(): string {
    const m = this.msg();
    if (!m) return "";
    return m.includes("actualizada") ? "text-emerald-400" : "text-rose-400";
  }

  async changePassword(): Promise<void> {
    this.msg.set(null);
    this.loading.set(true);
    const result = await this.auth.changePassword(this.currentPwd, this.newPwd);
    this.loading.set(false);
    this.msg.set(result.message);
    if (result.success) {
      this.currentPwd = "";
      this.newPwd = "";
    }
    setTimeout(() => this.msg.set(null), 5000);
  }

  async logout(): Promise<void> {
    this.loggingOut.set(true);
    await this.auth.logout();
    this.loggingOut.set(false);
  }
}
