# Yaaay Freshersの自動テストフレームワーク
Yaaay Freshersのウェブアプリケーションを効率的にテストするために設計されたSeleniumベースのテスト自動化フレームワークとなります。並列実行、テストレポート生成、JenkinsやDockerとのCI/CD統合をサポートします。

## 特徴
* **Selenium WebDriver**によるブラウザ操作の自動化
* **TestNG**によるテストケースの実装
* **Extent Reports**による詳細なテストレポート生成
* **TestNG**による並列実行のサポート
* **Jenkins & Docker**によるCI/CDの統合
* **ヘッドレスモード**対応で高速テスト実行
* **プロパティファイル**を使用した柔軟な設定管理
* テスト失敗時の**リトライ機能**
* テスト失敗時に**画面キャプチャ**の取得
* **Jsonファイル**で複数のデータセットでの実行

## 環境構築 & インストール
### 必要な環境：
* Java（JDK 11 以上）
* Maven（最新バージョン）
* Git（最新バージョン）
* ブラウザ
  * Chrome
  * Edge
* Docker（Dockerコンテナで実行する場合）
* Jenkins（CI/CD統合する場合）
### リポジトリをクローン：
* yaaay-freshers-testをclone
````
git clone https://github.com/PrasannaI21/yaaay-freshers-test.git
````
* ディレクトリ移動
```
cd yaaay-freshers-test
```
### ローカル環境でテスト実行：
* 全てのテストを実行
```
mvn test
```
* 特定のテストスイートを実行
```
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```
* Regressionプロフィールをヘッドレスで実行
```
mvn test -PRegression -Dbrowser=chromeheadless
```
## Docker & Jenkinsでのテスト実行
### Dockerでのテスト実行
* Docker HubからDockerイメージを取得
```
docker pull prasannai/inbound-agent
```
* コンテナを実行
```
docker run -rm prasannai/inbound-agent
```
### Jenkinsでのテスト実行
1. Jenkinsを開く
2. 該当するジョブを選択
3. Build with parametersでご希望のプロフィールやブラウザを選択
4. Build Nowをクリック

**レポート（Extent Reports & ログ）**  
テストの実行結果は**Extent Reports**で確認できます
* テスト実行後、レポートは以下に生成されます：  
`/reports/index.html`
* Jenkins 実行時は、レポートがメールで送信されます

## 使用言語/フレームワーク
* Java
* Page Object Model
