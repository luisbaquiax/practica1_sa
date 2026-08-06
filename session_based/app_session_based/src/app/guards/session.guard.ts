import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SessionAuthService } from '../services/session-auth.service';

export const sessionGuard: CanActivateFn = () => {
  const auth = inject(SessionAuthService);
  const router = inject(Router);
  if (auth.isLoggedIn()) return true;
  router.navigate(['/session/login']);
  return false;
};
