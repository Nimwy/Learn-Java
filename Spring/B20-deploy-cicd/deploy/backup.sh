
set -euo pipefail

source /etc/remoteit/remoteit.env

BACKUP_DIR="${BACKUP_DIR:-/var/backups/remoteit}"
DB_NAME="${DB_NAME:-remoteit}"
DB_USER="${DATABASE_USERNAME:-remoteit}"

mkdir -p "$BACKUP_DIR"
export PGPASSWORD="${DATABASE_PASSWORD:?Thiếu DATABASE_PASSWORD trong file env}"

pg_dump -h 127.0.0.1 -U "$DB_USER" -d "$DB_NAME" -Fc \
  -f "$BACKUP_DIR/remoteit-$(date +%F-%H%M).dump"


find "$BACKUP_DIR" -type f -name '*.dump' -mtime +7 -delete

echo "Backup xong: $BACKUP_DIR"
