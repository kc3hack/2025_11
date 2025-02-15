import csv

# 除外する2桁の数字（例: "01"）
EXCLUDE_CODES = {}#24~30

# 入力ファイルと出力ファイルの指定
input_file = "place_num.csv"
output_file = "kinki.csv"

# CSVを読み込んでフィルタリング
with open(input_file, newline='', encoding="utf-8") as infile, \
     open(output_file, "w", newline='', encoding="utf-8") as outfile:

    reader = csv.reader(infile)
    writer = csv.writer(outfile)

    for row in reader:
        if("24" <= row[2] and row[2] <= "30") :
            writer.writerow(row)

print("フィルタリング完了。")
