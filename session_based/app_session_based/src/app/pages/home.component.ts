import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  template: `
    <div class="space-y-10">
      <header class="text-center space-y-3 pt-6">
        <h1 class="text-4xl font-bold text-white">Session-Based Authentication</h1>
        <p class="text-slate-400 max-w-2xl mx-auto">
          Prueba de concepto interactiva para demostrar autenticación y autorización
          basada en sesiones (stateful) en aplicaciones web.
        </p>
      </header>

      <div class="grid md:grid-cols-2 gap-6">
        <div class="rounded-xl border border-slate-800 bg-slate-900 p-6 space-y-3 hover:border-cyan-500/50 transition">
          <div class="text-3xl"></div>
          <h2 class="text-xl font-semibold text-white">¿Qué es Session-Based?</h2>
          <p class="text-sm text-slate-400">
            Autenticación <strong class="text-slate-200">stateful</strong>: el servidor
            guarda el estado de la sesión en memoria/store. El cliente envía un cookie
            con el ID de sesión en cada petición.
          </p>
          <ul class="text-sm text-slate-400 space-y-1">
            <li>· Login / Logout</li>
            <li>· Cambio de contraseña con revocación</li>
            <li>· Bloqueo de usuario por admin</li>
            <li>· Páginas pública y protegida</li>
          </ul>
          <a routerLink="/session" class="inline-block mt-2 px-4 py-2 rounded-lg bg-cyan-500 text-slate-950 font-medium text-sm hover:bg-cyan-400 transition">
            Probar Session-Based
          </a>
        </div>

        <div class="rounded-xl border border-slate-800 bg-slate-900 p-6 space-y-3 hover:border-cyan-500/50 transition">
          <div class="text-3xl"></div>
          <h2 class="text-xl font-semibold text-white">Conceptos de la tarea</h2>
          <ul class="text-sm text-slate-400 space-y-1">
            <li>· Stateful vs Stateless</li>
            <li>· HTTP y el problema del estado</li>
            <li>· Trilema: seguridad, UX, performance</li>
            <li>· Estrategias de revocación</li>
            <li>· Escalabilidad horizontal</li>
          </ul>
          <a routerLink="/comparison" class="inline-block mt-2 px-4 py-2 rounded-lg border border-slate-700 text-slate-300 font-medium text-sm hover:bg-slate-800 transition">
            Ver comparación
          </a>
        </div>
      </div>
    </div>
  `,
})
export class HomeComponent {}
