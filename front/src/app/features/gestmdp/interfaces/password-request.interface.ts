export interface PasswordRequest {
  siteName: string;
  siteAddress?: string;
  siteLogin?: string;
  description?: string;
  passwordPrefix?: string;
  passwordLength?: number;
}
