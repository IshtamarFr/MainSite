export interface Password {
  id: number;
  siteName: string;
  siteAddress?: string;
  siteLogin?: string;
  description?: string;
  isActive: boolean;
  category_id: number;
  user_id: number;
}
