import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <div class="min-h-screen bg-slate-950 text-slate-200">
      <nav class="border-b border-slate-800 bg-slate-900/80 backdrop-blur sticky top-0 z-50">
        <div class="mx-auto max-w-6xl px-4 py-3 flex items-center gap-6 flex-wrap">
          <a routerLink="/" class="text-lg font-semibold text-white flex items-center gap-2">
            <span class="text-cyan-400"></span> Auth POC
          </a>
          <div class="flex gap-1 text-sm flex-wrap">
            <a routerLink="/" routerLinkActive="bg-slate-800 text-white"
               [routerLinkActiveOptions]="{ exact: true }"
               class="px-3 py-1.5 rounded-md hover:bg-slate-800 transition">Inicio</a>
            <a routerLink="/session" routerLinkActive="bg-slate-800 text-white"
               [routerLinkActiveOptions]="{ exact: true }"
               class="px-3 py-1.5 rounded-md hover:bg-slate-800 transition">Session-Based</a>
            <a routerLink="/comparison" routerLinkActive="bg-slate-800 text-white"
               [routerLinkActiveOptions]="{ exact: true }"
               class="px-3 py-1.5 rounded-md hover:bg-slate-800 transition">Comparación</a>
          </div>
        </div>
      </nav>
      <main class="mx-auto max-w-6xl px-4 py-8">
        <router-outlet />
      </main>
      <footer class="border-t border-slate-800 mt-12 py-6 text-center text-xs text-slate-500">
        POC Session-Based · Angular + Tailwind CSS
      </footer>
    </div>
  `,
})
export class App {}
