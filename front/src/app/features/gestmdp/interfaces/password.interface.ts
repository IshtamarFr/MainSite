export interface Password {
  id: number;
  siteName: string;
  siteAddress?: string;
  siteLogin?: string;
  description?: string;
  active: boolean;
  category_id: number;
  user_id: number;
}
