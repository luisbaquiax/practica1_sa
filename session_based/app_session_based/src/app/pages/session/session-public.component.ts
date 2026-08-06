import { Component, inject, signal, OnInit } from "@angular/core";
import { RouterLink } from "@angular/router";
import { SessionAuthService } from "../../services/session-auth.service";

@Component({
  selector: "app-session-public",
  imports: [RouterLink],
  template: `
    <div class="max-w-2xl mx-auto space-y-4">
      <header class="space-y-1">
        <h1 class="text-2xl font-bold text-white">Página Pública</h1>
        <p class="text-slate-400 text-sm">
          GET /api/auth/public Accesible sin iniciar sesión.
        </p>
      </header>
      <div
        class="rounded-lg border border-slate-800 bg-slate-900 p-6 space-y-3"
      >
        @if (loading()) {
        <p class="text-slate-500 text-sm">Cargando…</p>
        } @else {
        <p class="text-slate-300">{{ message() }}</p>
        <p class="text-slate-500 text-sm">
          No requiere autenticación. Cualquiera puede verla.
        </p>
        }
        <a
          routerLink="/session"
          class="inline-block text-sm text-cyan-400 hover:underline"
        >
          Volver</a
        >
      </div>
    </div>
  `,
})
export class SessionPublicComponent implements OnInit {
  private auth = inject(SessionAuthService);
  message = signal("");
  loading = signal(true);

  async ngOnInit(): Promise<void> {
    this.message.set(await this.auth.getPublicMessage());
    this.loading.set(false);
  }
}
