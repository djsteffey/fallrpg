package halfbyte.game.fallrpg;

public class GameState {
    public static class EntityState {
        // variables
        private long m_level;
        private long m_hpMax;
        private long m_hpCurrent;
        private long m_expNext;
        private long m_expCurrent;
        private long m_depthRecord;
        private long m_currency;


        // methods
        public EntityState(long level, long hpMax){
            this.m_level = level;
            this.m_hpMax = hpMax;
            this.m_hpCurrent = this.m_hpMax;
            this.m_expNext = Config.computeNextExp(this.m_level + 1);
            this.m_expCurrent = 0;
            this.m_depthRecord = 0;
            this.m_currency = 0;
        }

        public long getLevel(){
            return this.m_level;
        }

        public void incrementLevel(){
            this.m_level += 1;
        }

        public long getHpMax(){
            return this.m_hpMax;
        }

        public long getHpCurrent(){
            return this.m_hpCurrent;
        }

        public long getExpNext(){
            return this.m_expNext;
        }

        public long getExpCurrent(){
            return this.m_expCurrent;
        }

        public void resetHp(){
            this.m_hpCurrent = this.m_hpMax;
        }

        public void setHpCurrent(long hp){
            this.m_hpCurrent = hp;
        }

        public void damage(long amount){
            this.m_hpCurrent = Math.max(this.m_hpCurrent - amount, 0);
        }

        public void kill(){
            this.m_hpCurrent = 0;
        }

        public void gainExp(long amount){
            this.m_expCurrent += amount;
            while (this.m_expCurrent >= this.m_expNext){
                this.incrementLevel();
                this.m_expCurrent -= this.m_expNext;
                this.m_expNext = Config.computeNextExp(this.m_level + 1);
            }
        }

        public void updateRecordDepth(long depth){
            // update depth
            if (depth < this.m_depthRecord){
                this.m_depthRecord = depth;
            }
        }

        public long getCurrency(){
            return this.m_currency;
        }

        public void modifyCurrency(long amount){
            this.m_currency += amount;
        }
    }

    // variables
    private EntityState m_playerState;

    // methods
    public GameState(){
        this.m_playerState = new EntityState(1, 20);
    }

    public EntityState getPlayerState(){
        return this.m_playerState;
    }
}
