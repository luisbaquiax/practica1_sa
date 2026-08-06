import { Component } from '@angular/core';

interface ComparisonRow {
  aspect: string;
  session: string;
  jwt: string;
}

@Component({
  selector: 'app-comparison',
  template: `
    <div class="space-y-8">
      <header class="space-y-2">
        <h1 class="text-3xl font-bold text-white">Comparación detallada</h1>
        <p class="text-slate-400">Session-Based (stateful) vs JWT (stateless)</p>
      </header>

      <!-- Conceptos clave -->
      <section class="space-y-4">
        <h2 class="text-xl font-semibold text-cyan-400">Conceptos clave</h2>
        <div class="grid md:grid-cols-3 gap-4">
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-white">Stateful vs Stateless</h3>
            <p class="text-sm text-slate-400">Session guarda el estado en el servidor (memoria/DB). JWT no guarda estado: el token contiene toda la información y se valida con la firma.</p>
          </div>
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-white">HTTP y el problema del estado</h3>
            <p class="text-sm text-slate-400">HTTP es sin estado por diseño. Cada petición es independiente, por eso se necesita un mecanismo (cookie o token) para identificar al usuario entre peticiones.</p>
          </div>
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-white">Trilema de autenticación</h3>
            <p class="text-sm text-slate-400">Seguridad, experiencia de usuario y performance. Mejorar uno suele comprometer otro. Session prioriza seguridad; JWT prioriza performance/escalabilidad.</p>
          </div>
        </div>
      </section>

      <!-- Tabla comparativa -->
      <section class="overflow-x-auto">
        <table class="w-full text-sm border border-slate-800 rounded-lg overflow-hidden">
          <thead>
            <tr class="bg-slate-900 text-left">
              <th class="px-4 py-3 font-medium text-slate-300">Aspecto</th>
              <th class="px-4 py-3 font-medium text-cyan-400">Session-Based</th>
              <th class="px-4 py-3 font-medium text-amber-400">JWT</th>
            </tr>
          </thead>
          <tbody>
            @for (row of rows; track row.aspect) {
              <tr class="border-t border-slate-800 hover:bg-slate-900/50">
                <td class="px-4 py-3 font-medium text-white">{{ row.aspect }}</td>
                <td class="px-4 py-3 text-slate-400">{{ row.session }}</td>
                <td class="px-4 py-3 text-slate-400">{{ row.jwt }}</td>
              </tr>
            }
          </tbody>
        </table>
      </section>

      <!-- Estrategias de revocación -->
      <section class="space-y-4">
        <h2 class="text-xl font-semibold text-amber-400">Estrategias de revocación</h2>
        <div class="space-y-3">
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4">
            <h3 class="font-medium text-white mb-1">Usuario cambia contraseña</h3>
            <p class="text-sm text-slate-400"><strong class="text-cyan-400">Session:</strong> el servidor elimina las demás sesiones de la tabla de sesiones — revocación inmediata y total.</p>
            <p class="text-sm text-slate-400 mt-1"><strong class="text-amber-400">JWT:</strong> se incrementa un <code class="text-slate-300">pwVersion</code> en el claim del token. Los tokens antiguos dejan de validar porque su versión no coincide. No requiere lista de revocación.</p>
          </div>
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4">
            <h3 class="font-medium text-white mb-1">Admin bloquea usuario</h3>
            <p class="text-sm text-slate-400"><strong class="text-cyan-400">Session:</strong> se eliminan todas las sesiones del usuario de la tabla. Efecto inmediato.</p>
            <p class="text-sm text-slate-400 mt-1"><strong class="text-amber-400">JWT:</strong> el servidor marca <code class="text-slate-300">active = false</code>. En el próximo request, la validación del token falla porque verifica el estado del usuario. Hasta entonces el token sigue siendo válido (ventana de vulnerabilidad).</p>
          </div>
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4">
            <h3 class="font-medium text-white mb-1">Logout manual y por timeout</h3>
            <p class="text-sm text-slate-400"><strong class="text-cyan-400">Session:</strong> logout elimina la sesión de la tabla. Timeout: el servidor purga sesiones inactivas periódicamente.</p>
            <p class="text-sm text-slate-400 mt-1"><strong class="text-amber-400">JWT:</strong> logout añade el <code class="text-slate-300">jti</code> del token a una lista de revocación (rompe el stateless). Timeout: el token expira automáticamente por el claim <code class="text-slate-300">exp</code>, sin intervención del servidor.</p>
          </div>
        </div>
      </section>

      <!-- Escalabilidad -->
      <section class="space-y-4">
        <h2 class="text-xl font-semibold text-emerald-400">Escalabilidad horizontal</h2>
        <div class="grid md:grid-cols-2 gap-4">
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-cyan-400">Session-Based</h3>
            <p class="text-sm text-slate-400">Si se replica en varios servidores, la sesión debe compartirse (store externo como Redis) o usar sticky sessions. Sin store compartido, un usuario podría cerrar sesión en un nodo y no en otro.</p>
          </div>
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-amber-400">JWT</h3>
            <p class="text-sm text-slate-400">Cualquier servidor puede validar el token con solo la clave pública/secreta. No necesita store compartido. Ideal para arquitecturas distribuidas y microservicios.</p>
          </div>
        </div>
      </section>

      <!-- Seguridad -->
      <section class="space-y-4">
        <h2 class="text-xl font-semibold text-rose-400">Seguridad: problemas y soluciones</h2>
        <div class="grid md:grid-cols-2 gap-4">
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-cyan-400">Session-Based</h3>
            <ul class="text-sm text-slate-400 space-y-1.5">
              <li>· <strong class="text-slate-200">Session hijacking</strong> → cookie con <code>httpOnly</code> + <code>secure</code> + <code>sameSite</code></li>
              <li>· <strong class="text-slate-200">CSRF</strong> → token anti-CSRF o <code>sameSite=strict</code></li>
              <li>· <strong class="text-slate-200">Fixation</strong> → regenerar ID de sesión tras login</li>
              <li>· <strong class="text-slate-200">Store compartido</strong> → punto único de fallo si Redis cae</li>
            </ul>
          </div>
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-amber-400">JWT</h3>
            <ul class="text-sm text-slate-400 space-y-1.5">
              <li>· <strong class="text-slate-200">XSS roba el token</strong> → guardar en memoria o cookie <code>httpOnly</code></li>
              <li>· <strong class="text-slate-200">No revocable</strong> → lista de revocación (rompe stateless) o tokens de corta vida + refresh</li>
              <li>· <strong class="text-slate-200">Clave comprometida</strong> → rotar clave de firma; todos los tokens quedan invalidados</li>
              <li>· <strong class="text-slate-200">Payload visible</strong> → no guardar datos sensibles (solo Base64, no cifrado)</li>
            </ul>
          </div>
        </div>
      </section>

      <!-- UX -->
      <section class="space-y-4">
        <h2 class="text-xl font-semibold text-violet-400">Experiencia de usuario</h2>
        <div class="grid md:grid-cols-2 gap-4">
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-cyan-400">Session-Based</h3>
            <p class="text-sm text-slate-400">El navegador maneja la cookie automáticamente. El usuario no nota nada. Logout es inmediato y efectivo. Si el store cae, todas las sesiones se pierden.</p>
          </div>
          <div class="rounded-lg border border-slate-800 bg-slate-900 p-4 space-y-2">
            <h3 class="font-medium text-amber-400">JWT</h3>
            <p class="text-sm text-slate-400">El token puede guardarse en localStorage (vulnerable a XSS) o en cookie. El refresh puede ser transparente. Logout no es efectivo a menos que se use lista de revocación.</p>
          </div>
        </div>
      </section>
    </div>
  `,
})
export class ComparisonComponent {
  rows: ComparisonRow[] = [
    { aspect: 'Estado en el servidor', session: 'Stateful: guarda la sesión en memoria/store', jwt: 'Stateless: no guarda estado, el token lo contiene' },
    { aspect: 'Dónde se guarda en el cliente', session: 'Cookie httpOnly (no accesible por JS)', jwt: 'localStorage, sessionStorage o cookie' },
    { aspect: 'Cómo se envía al servidor', session: 'Automático vía cookie', jwt: 'Header Authorization: Bearer <token>' },
    { aspect: 'Tamaño de la petición', session: 'Pequeño (solo ID de sesión)', jwt: 'Mayor (token completo con claims)' },
    { aspect: 'Revocación inmediata', session: 'Sí: basta con borrar la sesión del store', jwt: 'No nativamente: requiere lista de revocación o expiración' },
    { aspect: 'Escalabilidad horizontal', session: 'Requiere store compartido (Redis) o sticky sessions', jwt: 'Natural: cualquier nodo valida con la clave' },
    { aspect: 'CSRF', session: 'Vulnerable: requiere protección anti-CSRF', jwt: 'No vulnerable (si se usa solo en header Authorization)' },
    { aspect: 'XSS', session: 'Cookie httpOnly protege el token de robo por JS', jwt: 'Si está en localStorage, XSS puede robarlo' },
    { aspect: 'Logout', session: 'Inmediato y efectivo', jwt: 'Requiere lista de revocación para ser efectivo' },
    { aspect: 'Timeout', session: 'Servidor purga sesiones inactivas', jwt: 'Token expira solo por el claim exp' },
    { aspect: 'Cambio de contraseña', session: 'Eliminar sesiones del usuario en el store', jwt: 'Incrementar pwVersion en el claim' },
    { aspect: 'Bloqueo de usuario', session: 'Eliminar sesiones: efecto inmediato', jwt: 'Fallar validación en próximo request (ventana de riesgo)' },
    { aspect: 'Microservicios', session: 'Complejo: necesita store compartido', jwt: 'Ideal: cada servicio valida de forma independiente' },
  ];
}
