import { Injectable, signal, computed } from "@angular/core";
import { AuthResult } from "../models/auth.models";

const BASE_URL = "http://localhost:3001/api/auth";

@Injectable({ providedIn: "root" })
export class SessionAuthService {
  readonly username = signal<string | null>(null);
  readonly isLoggedIn = computed(() => this.username() !== null);

  constructor() {
    this.checkSession();
  }

  private async request<T>(
    path: string,
    options: RequestInit = {}
  ): Promise<T & { ok: boolean; status: number }> {
    const res = await fetch(`${BASE_URL}${path}`, {
      ...options,
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        ...(options.headers ?? {}),
      },
    });

    let data: any = {};
    try {
      data = await res.json();
    } catch {
      // respuesta sin body
    }

    return { ...data, ok: res.ok, status: res.status };
  }

  // Al cargar la app, pregunta al servidor si hay sesión activa (la cookie viaja sola)
  async checkSession(): Promise<void> {
    try {
      const res = await this.request<{ message?: string }>("/protected");
      if (res.ok && res.message) {
        // El servidor responde "Hola <username>, esta es una página protegida."
        const match = res.message.match(/Hola\s+(\S+),/);
        if (match) this.username.set(match[1]);
        else this.username.set("usuario");
      } else {
        this.username.set(null);
      }
    } catch {
      this.username.set(null);
    }
  }

  async login(username: string, password: string): Promise<AuthResult> {
    try {
      const res = await this.request<{ message: string; username?: string }>(
        "/login",
        {
          method: "POST",
          body: JSON.stringify({ username, password }),
        }
      );

      if (res.ok) {
        this.username.set(res.username ?? username);
        return { success: true, message: res.message, username: res.username };
      }
      return {
        success: false,
        message: res.message ?? "Error al iniciar sesión.",
      };
    } catch {
      return {
        success: false,
        message: "No se pudo conectar con el servidor.",
      };
    }
  }

  async logout(): Promise<AuthResult> {
    try {
      const res = await this.request<{ message: string }>("/logout", {
        method: "POST",
      });
      this.username.set(null);
      return { success: res.ok, message: res.message ?? "Sesión cerrada." };
    } catch {
      this.username.set(null);
      return {
        success: false,
        message: "No se pudo conectar con el servidor.",
      };
    }
  }

  async changePassword(
    currentPassword: string,
    newPassword: string
  ): Promise<AuthResult> {
    try {
      const res = await this.request<{ message: string }>("/change-password", {
        method: "POST",
        body: JSON.stringify({ currentPassword, newPassword }),
      });

      if (res.ok) {
        return {
          success: true,
          message: res.message ?? "Contraseña actualizada.",
        };
      }
      return {
        success: false,
        message: res.message ?? "Error al cambiar la contraseña.",
      };
    } catch {
      return {
        success: false,
        message: "No se pudo conectar con el servidor.",
      };
    }
  }

  async getProtectedMessage(): Promise<string> {
    try {
      const res = await this.request<{ message: string }>("/protected");
      return res.ok ? res.message : "No autorizado.";
    } catch {
      return "No se pudo conectar con el servidor.";
    }
  }

  async getPublicMessage(): Promise<string> {
    try {
      const res = await this.request<{ message: string }>("/public");
      return res.ok ? res.message : "Error.";
    } catch {
      return "No se pudo conectar con el servidor.";
    }
  }
}
